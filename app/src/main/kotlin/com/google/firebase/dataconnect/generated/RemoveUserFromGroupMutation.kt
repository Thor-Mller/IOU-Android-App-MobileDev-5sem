
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



public interface RemoveUserFromGroupMutation :
    com.google.firebase.dataconnect.generated.GeneratedMutation<
      ExampleConnector,
      RemoveUserFromGroupMutation.Data,
      RemoveUserFromGroupMutation.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val groupId: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
  
    val userId: String,
  
  ) {
    
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val groupUser_delete: GroupUserKey?,
  
  ) {
    
    
  }
  

  public companion object {
    public val operationName: String = "RemoveUserFromGroup"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun RemoveUserFromGroupMutation.ref(
  
    groupId: java.util.UUID,userId: String,

  
  
): com.google.firebase.dataconnect.MutationRef<
    RemoveUserFromGroupMutation.Data,
    RemoveUserFromGroupMutation.Variables
  > =
  ref(
    
      RemoveUserFromGroupMutation.Variables(
        groupId=groupId,userId=userId,
  
      )
    
  )

public suspend fun RemoveUserFromGroupMutation.execute(

  
    
      groupId: java.util.UUID,userId: String,

  

  ): com.google.firebase.dataconnect.MutationResult<
    RemoveUserFromGroupMutation.Data,
    RemoveUserFromGroupMutation.Variables
  > =
  ref(
    
      groupId=groupId,userId=userId,
  
    
  ).execute()


