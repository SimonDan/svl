package com.github.simondan.svl.communication.utils;

import com.google.gson.*;

import java.lang.reflect.*;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * @author Simon Danner, 09.12.2019
 */
public final class GsonFactory
{
  public static final Gson GSON = new GsonBuilder()
      .registerTypeHierarchyAdapter(Object.class, new _Serializer())
      .registerTypeHierarchyAdapter(Object.class, new _Deserializer())
      .create();

  private static final Gson DEFAULT_GSON = new Gson();

  private GsonFactory()
  {
  }

  private static class _Serializer implements JsonSerializer<Object>
  {


    @Override
    public JsonElement serialize(Object pObject, Type pType, JsonSerializationContext pContext)
    {
      if (!(pType instanceof Class))
        return DEFAULT_GSON.toJsonTree(pObject, pType);

      final Class<?> objectType = (Class<?>) pType;

      if (!objectType.isInterface())
        return DEFAULT_GSON.toJsonTree(pObject, pType);

      final Function<Method, JsonElement> valueConverter = pMethod -> pContext.serialize(_invoke(pMethod, pObject));

      return Stream.of(objectType.getDeclaredMethods())
          .filter(pMethod -> pMethod.getReturnType() != Void.class)
          .filter(pMethod -> pMethod.getParameterCount() == 0)
          .reduce(new JsonObject(), (pJson, pMethod) ->
          {
            pJson.add(pMethod.getName(), valueConverter.apply(pMethod));
            return pJson;
          }, (j1, j2) -> j1);
    }

    private static Object _invoke(Method pMethod, Object pInstance)
    {
      try
      {
        return pMethod.invoke(pInstance);
      }
      catch (IllegalAccessException | InvocationTargetException pE)
      {
        throw new RuntimeException(pE);
      }
    }
  }

  private static class _Deserializer implements JsonDeserializer<Object>
  {
    @Override
    public Object deserialize(JsonElement pJson, Type pType, JsonDeserializationContext pContext) throws JsonParseException
    {
      if (!(pType instanceof Class))
        return DEFAULT_GSON.fromJson(pJson, pType);

      final Class<?> objectType = (Class<?>) pType;

      if (!objectType.isInterface())
        return DEFAULT_GSON.fromJson(pJson, pType);

      final JsonObject jsonObject = pJson.getAsJsonObject();

      return Proxy.newProxyInstance(GsonFactory.class.getClassLoader(), new Class<?>[]{objectType}, (pObject, pMethod, pArgs) ->
          DEFAULT_GSON.fromJson(jsonObject.get(pMethod.getName()), pMethod.getReturnType()));
    }
  }
}
