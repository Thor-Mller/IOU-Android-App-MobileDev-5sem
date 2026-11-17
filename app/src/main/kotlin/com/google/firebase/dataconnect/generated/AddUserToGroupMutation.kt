
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



public interface AddUserToGroupMutation :
    com.google.firebase.dataconnect.generated.GeneratedMutation<
      ExampleConnector,
      AddUserToGroupMutation.Data,
      AddUserToGroupMutation.Variables
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
  
    val groupUser_insert: GroupUserKey,
  
  ) {
    
    
  }
  

  public companion object {
    public val operationName: String = "AddUserToGroup"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun AddUserToGroupMutation.ref(
  
    groupId: java.util.UUID,userId: String,

  
  
): com.google.firebase.dataconnect.MutationRef<
    AddUserToGroupMutation.Data,
    AddUserToGroupMutation.Variables
  > =
  ref(
    
      AddUserToGroupMutation.Variables(
        groupId=groupId,userId=userId,
  
      )
    
  )

public suspend fun AddUserToGroupMutation.execute(

  
    
      groupId: java.util.UUID,userId: String,

  

  ): com.google.firebase.dataconnect.MutationResult<
    AddUserToGroupMutation.Data,
    AddUserToGroupMutation.Variables
  > =
  ref(
    
      groupId=groupId,userId=userId,
  
    
  ).execute()


