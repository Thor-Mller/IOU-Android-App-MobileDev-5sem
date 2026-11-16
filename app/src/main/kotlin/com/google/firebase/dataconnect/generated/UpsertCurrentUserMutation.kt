
@file:Suppress(
  "KotlinRedundantDiagnosticSuppress",
  "PropertyName",
  "MayBeConstant",
  "RedundantVisibilityModifier",
  "RedundantCompanionReference",
  "RemoveEmptyClassBody",
  "SpellCheckingInspection",
  "unused",
)

package com.google.firebase.dataconnect.generated



public interface UpsertCurrentUserMutation :
    com.google.firebase.dataconnect.generated.GeneratedMutation<
      ExampleConnector,
      UpsertCurrentUserMutation.Data,
      UpsertCurrentUserMutation.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val userId: String,
  
    val username: String,
  
    val phone: String,
  
  ) {
    
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val user_upsert: UserKey,
  
  ) {
    
    
  }
  

  public companion object {
    public val operationName: String = "UpsertCurrentUser"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun UpsertCurrentUserMutation.ref(
  
    userId: String,username: String,phone: String,

  
  
): com.google.firebase.dataconnect.MutationRef<
    UpsertCurrentUserMutation.Data,
    UpsertCurrentUserMutation.Variables
  > =
  ref(
    
      UpsertCurrentUserMutation.Variables(
        userId=userId,username=username,phone=phone,
  
      )
    
  )

public suspend fun UpsertCurrentUserMutation.execute(

  
    
      userId: String,username: String,phone: String,

  

  ): com.google.firebase.dataconnect.MutationResult<
    UpsertCurrentUserMutation.Data,
    UpsertCurrentUserMutation.Variables
  > =
  ref(
    
      userId=userId,username=username,phone=phone,
  
    
  ).execute()


