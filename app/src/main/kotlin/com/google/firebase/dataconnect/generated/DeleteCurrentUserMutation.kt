
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



public interface DeleteCurrentUserMutation :
    com.google.firebase.dataconnect.generated.GeneratedMutation<
      ExampleConnector,
      DeleteCurrentUserMutation.Data,
      Unit
    >
{
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val expenseProvider_deleteMany: Int,
  
    val expense_deleteMany: Int,
  
    val groupUser_deleteMany: Int,
  
    val group_deleteMany: Int,
  
    val user_delete: UserKey?,
  
  ) {
    
    
  }
  

  public companion object {
    public val operationName: String = "DeleteCurrentUser"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Unit> =
      kotlinx.serialization.serializer()
  }
}

public fun DeleteCurrentUserMutation.ref(
  
): com.google.firebase.dataconnect.MutationRef<
    DeleteCurrentUserMutation.Data,
    Unit
  > =
  ref(
    
      Unit
    
  )

public suspend fun DeleteCurrentUserMutation.execute(

  

  ): com.google.firebase.dataconnect.MutationResult<
    DeleteCurrentUserMutation.Data,
    Unit
  > =
  ref(
    
  ).execute()


