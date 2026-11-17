
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



public interface CreateCurrentUserMutation :
    GeneratedMutation<
      ExampleConnector,
      CreateCurrentUserMutation.Data,
      CreateCurrentUserMutation.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val username: String,
  
    val phone: String,
  
    val email: String,
  
  ) {
    
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val user_upsert: UserKey,
  
  ) {
    
    
  }
  

  public companion object {
    public val operationName: String = "CreateCurrentUser"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun CreateCurrentUserMutation.ref(
  
    username: String,phone: String,email: String,

  
  
): com.google.firebase.dataconnect.MutationRef<
    CreateCurrentUserMutation.Data,
    CreateCurrentUserMutation.Variables
  > =
  ref(
    
      CreateCurrentUserMutation.Variables(
        username=username,phone=phone,email=email,
  
      )
    
  )

public suspend fun CreateCurrentUserMutation.execute(

  
    
      username: String,phone: String,email: String,

  

  ): com.google.firebase.dataconnect.MutationResult<
    CreateCurrentUserMutation.Data,
    CreateCurrentUserMutation.Variables
  > =
  ref(
    
      username=username,phone=phone,email=email,
  
    
  ).execute()


