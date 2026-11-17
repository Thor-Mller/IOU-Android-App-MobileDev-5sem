
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



public interface UpdateGroupMutation :
    com.google.firebase.dataconnect.generated.GeneratedMutation<
      ExampleConnector,
      UpdateGroupMutation.Data,
      UpdateGroupMutation.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val groupId: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
  
    val name: String,
  
  ) {
    
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val group_update: GroupKey?,
  
  ) {
    
    
  }
  

  public companion object {
    public val operationName: String = "UpdateGroup"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun UpdateGroupMutation.ref(
  
    groupId: java.util.UUID,name: String,

  
  
): com.google.firebase.dataconnect.MutationRef<
    UpdateGroupMutation.Data,
    UpdateGroupMutation.Variables
  > =
  ref(
    
      UpdateGroupMutation.Variables(
        groupId=groupId,name=name,
  
      )
    
  )

public suspend fun UpdateGroupMutation.execute(

  
    
      groupId: java.util.UUID,name: String,

  

  ): com.google.firebase.dataconnect.MutationResult<
    UpdateGroupMutation.Data,
    UpdateGroupMutation.Variables
  > =
  ref(
    
      groupId=groupId,name=name,
  
    
  ).execute()


