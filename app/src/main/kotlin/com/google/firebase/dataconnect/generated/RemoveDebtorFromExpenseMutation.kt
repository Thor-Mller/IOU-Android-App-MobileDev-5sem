
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



public interface RemoveDebtorFromExpenseMutation :
    com.google.firebase.dataconnect.generated.GeneratedMutation<
      ExampleConnector,
      RemoveDebtorFromExpenseMutation.Data,
      RemoveDebtorFromExpenseMutation.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val expenseId: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
  
    val debtorUserId: String,
  
  ) {
    
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val expenseProvider_delete: ExpenseProviderKey?,
  
  ) {
    
    
  }
  

  public companion object {
    public val operationName: String = "RemoveDebtorFromExpense"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun RemoveDebtorFromExpenseMutation.ref(
  
    expenseId: java.util.UUID,debtorUserId: String,

  
  
): com.google.firebase.dataconnect.MutationRef<
    RemoveDebtorFromExpenseMutation.Data,
    RemoveDebtorFromExpenseMutation.Variables
  > =
  ref(
    
      RemoveDebtorFromExpenseMutation.Variables(
        expenseId=expenseId,debtorUserId=debtorUserId,
  
      )
    
  )

public suspend fun RemoveDebtorFromExpenseMutation.execute(

  
    
      expenseId: java.util.UUID,debtorUserId: String,

  

  ): com.google.firebase.dataconnect.MutationResult<
    RemoveDebtorFromExpenseMutation.Data,
    RemoveDebtorFromExpenseMutation.Variables
  > =
  ref(
    
      expenseId=expenseId,debtorUserId=debtorUserId,
  
    
  ).execute()


