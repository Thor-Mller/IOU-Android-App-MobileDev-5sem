
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



public interface DeleteExpenseMutation :
    GeneratedMutation<
      ExampleConnector,
      DeleteExpenseMutation.Data,
      DeleteExpenseMutation.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val expenseId: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
  
  ) {
    
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val expense_delete: ExpenseKey?,
  
  ) {
    
    
  }
  

  public companion object {
    public val operationName: String = "DeleteExpense"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun DeleteExpenseMutation.ref(
  
    expenseId: java.util.UUID,

  
  
): com.google.firebase.dataconnect.MutationRef<
    DeleteExpenseMutation.Data,
    DeleteExpenseMutation.Variables
  > =
  ref(
    
      DeleteExpenseMutation.Variables(
        expenseId=expenseId,
  
      )
    
  )

public suspend fun DeleteExpenseMutation.execute(

  
    
      expenseId: java.util.UUID,

  

  ): com.google.firebase.dataconnect.MutationResult<
    DeleteExpenseMutation.Data,
    DeleteExpenseMutation.Variables
  > =
  ref(
    
      expenseId=expenseId,
  
    
  ).execute()


