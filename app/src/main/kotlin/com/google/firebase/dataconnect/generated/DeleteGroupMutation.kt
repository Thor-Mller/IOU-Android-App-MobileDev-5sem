
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



public interface DeleteGroupMutation :
    com.google.firebase.dataconnect.generated.GeneratedMutation<
      ExampleConnector,
      DeleteGroupMutation.Data,
      DeleteGroupMutation.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val groupId: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
  
  ) {
    
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val groupUser_deleteMany: Int,
  
    val group_delete: GroupKey?,
  
  ) {
    
    
  }
  

  public companion object {
    public val operationName: String = "DeleteGroup"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun DeleteGroupMutation.ref(
  
    groupId: java.util.UUID,

  
  
): com.google.firebase.dataconnect.MutationRef<
    DeleteGroupMutation.Data,
    DeleteGroupMutation.Variables
  > =
  ref(
    
      DeleteGroupMutation.Variables(
        groupId=groupId,
  
      )
    
  )

public suspend fun DeleteGroupMutation.execute(

  
    
      groupId: java.util.UUID,

  

  ): com.google.firebase.dataconnect.MutationResult<
    DeleteGroupMutation.Data,
    DeleteGroupMutation.Variables
  > =
  ref(
    
      groupId=groupId,
  
    
  ).execute()


