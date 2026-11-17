
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


import kotlinx.coroutines.flow.filterNotNull as _flow_filterNotNull
import kotlinx.coroutines.flow.map as _flow_map


public interface GetMyProfileAndExpensesQuery :
    GeneratedQuery<
      ExampleConnector,
      GetMyProfileAndExpensesQuery.Data,
      Unit
    >
{
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val user: User?,
  
  ) {
    
      
        @kotlinx.serialization.Serializable
  public data class User(
  
    val id: String,
  
    val username: String,
  
    val phone: String,
  
    val email: String,
  
    val totalMissing: Double,
  
    val totalDebt: Double,
  
    val expenses_on_user: List<ExpensesOnUserItem>,
  
    val expenseProviders_on_user: List<ExpenseProvidersOnUserItem>,
  
  ) {
    
      
        @kotlinx.serialization.Serializable
  public data class ExpensesOnUserItem(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
  
    val name: String,
  
    val amount: Double,
  
    val expenseProviders_on_expense: List<ExpenseProvidersOnExpenseItem>,
  
  ) {
    
      
        @kotlinx.serialization.Serializable
  public data class ExpenseProvidersOnExpenseItem(
  
    val shareAmount: Double?,
  
    val user: User,
  
  ) {
    
      
        @kotlinx.serialization.Serializable
  public data class User(
  
    val id: String,
  
    val username: String,
  
  ) {
    
    
  }
      
    
    
  }
      
    
    
  }
      
        @kotlinx.serialization.Serializable
  public data class ExpenseProvidersOnUserItem(
  
    val shareAmount: Double?,
  
    val expense: Expense,
  
  ) {
    
      
        @kotlinx.serialization.Serializable
  public data class Expense(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
  
    val name: String,
  
    val amount: Double,
  
    val user: User,
  
  ) {
    
      
        @kotlinx.serialization.Serializable
  public data class User(
  
    val id: String,
  
    val username: String,
  
  ) {
    
    
  }
      
    
    
  }
      
    
    
  }
      
    
    
  }
      
    
    
  }
  

  public companion object {
    public val operationName: String = "GetMyProfileAndExpenses"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Unit> =
      kotlinx.serialization.serializer()
  }
}

public fun GetMyProfileAndExpensesQuery.ref(
  
): com.google.firebase.dataconnect.QueryRef<
    GetMyProfileAndExpensesQuery.Data,
    Unit
  > =
  ref(
    
      Unit
    
  )

public suspend fun GetMyProfileAndExpensesQuery.execute(

  
    fetchPolicy: com.google.firebase.dataconnect.QueryRef.FetchPolicy = com.google.firebase.dataconnect.QueryRef.FetchPolicy.PREFER_CACHE,
  

  ): com.google.firebase.dataconnect.QueryResult<
    GetMyProfileAndExpensesQuery.Data,
    Unit
  > =
  ref(
    
  ).execute(fetchPolicy = fetchPolicy)


  public fun GetMyProfileAndExpensesQuery.flow(
    
    ): kotlinx.coroutines.flow.Flow<GetMyProfileAndExpensesQuery.Data> =
    ref(
        
      ).subscribe()
      .flow
      ._flow_map { querySubscriptionResult -> querySubscriptionResult.result.getOrNull() }
      ._flow_filterNotNull()
      ._flow_map { it.data }

