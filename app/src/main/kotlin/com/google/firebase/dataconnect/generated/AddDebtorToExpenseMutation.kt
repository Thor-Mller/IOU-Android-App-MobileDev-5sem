
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



public interface AddDebtorToExpenseMutation :
    com.google.firebase.dataconnect.generated.GeneratedMutation<
      ExampleConnector,
      AddDebtorToExpenseMutation.Data,
      AddDebtorToExpenseMutation.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val expenseId: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
  
    val debtorUserId: String,
  
    val shareAmount: Double,
  
  ) {
    
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val expenseProvider_insert: ExpenseProviderKey,
  
  ) {
    
    
  }
  

  public companion object {
    public val operationName: String = "AddDebtorToExpense"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun AddDebtorToExpenseMutation.ref(
  
    expenseId: java.util.UUID,debtorUserId: String,shareAmount: Double,

  
  
): com.google.firebase.dataconnect.MutationRef<
    AddDebtorToExpenseMutation.Data,
    AddDebtorToExpenseMutation.Variables
  > =
  ref(
    
      AddDebtorToExpenseMutation.Variables(
        expenseId=expenseId,debtorUserId=debtorUserId,shareAmount=shareAmount,
  
      )
    
  )

public suspend fun AddDebtorToExpenseMutation.execute(

  
    
      expenseId: java.util.UUID,debtorUserId: String,shareAmount: Double,

  

  ): com.google.firebase.dataconnect.MutationResult<
    AddDebtorToExpenseMutation.Data,
    AddDebtorToExpenseMutation.Variables
  > =
  ref(
    
      expenseId=expenseId,debtorUserId=debtorUserId,shareAmount=shareAmount,
  
    
  ).execute()


