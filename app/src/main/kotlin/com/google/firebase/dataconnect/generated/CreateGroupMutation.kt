
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



public interface CreateGroupMutation :
    GeneratedMutation<
      ExampleConnector,
      CreateGroupMutation.Data,
      CreateGroupMutation.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val name: String,
  
  ) {
    
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val group_insert: GroupKey,
  
  ) {
    
    
  }
  

  public companion object {
    public val operationName: String = "CreateGroup"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun CreateGroupMutation.ref(
  
    name: String,

  
  
): com.google.firebase.dataconnect.MutationRef<
    CreateGroupMutation.Data,
    CreateGroupMutation.Variables
  > =
  ref(
    
      CreateGroupMutation.Variables(
        name=name,
  
      )
    
  )

public suspend fun CreateGroupMutation.execute(

  
    
      name: String,

  

  ): com.google.firebase.dataconnect.MutationResult<
    CreateGroupMutation.Data,
    CreateGroupMutation.Variables
  > =
  ref(
    
      name=name,
  
    
  ).execute()


