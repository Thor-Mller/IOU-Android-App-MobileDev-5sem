
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



public interface CreateExpenseMutation :
    com.google.firebase.dataconnect.generated.GeneratedMutation<
      ExampleConnector,
      CreateExpenseMutation.Data,
      CreateExpenseMutation.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val name: String,
  
    val amount: Double,
  
  ) {
    
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val expense_insert: ExpenseKey,
  
  ) {
    
    
  }
  

  public companion object {
    public val operationName: String = "CreateExpense"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun CreateExpenseMutation.ref(
  
    name: String,amount: Double,

  
  
): com.google.firebase.dataconnect.MutationRef<
    CreateExpenseMutation.Data,
    CreateExpenseMutation.Variables
  > =
  ref(
    
      CreateExpenseMutation.Variables(
        name=name,amount=amount,
  
      )
    
  )

public suspend fun CreateExpenseMutation.execute(

  
    
      name: String,amount: Double,

  

  ): com.google.firebase.dataconnect.MutationResult<
    CreateExpenseMutation.Data,
    CreateExpenseMutation.Variables
  > =
  ref(
    
      name=name,amount=amount,
  
    
  ).execute()


