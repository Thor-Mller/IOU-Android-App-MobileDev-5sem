
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


public interface GetExpenseByIdQuery :
    com.google.firebase.dataconnect.generated.GeneratedQuery<
      ExampleConnector,
      GetExpenseByIdQuery.Data,
      GetExpenseByIdQuery.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val expenseId: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
  
  ) {
    
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val expense: Expense?,
  
  ) {
    
      
        @kotlinx.serialization.Serializable
  public data class Expense(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
  
    val name: String,
  
    val amount: Double,
  
    val user: User,
  
    val expenseProviders_on_expense: List<ExpenseProvidersOnExpenseItem>,
  
  ) {
    
      
        @kotlinx.serialization.Serializable
  public data class User(
  
    val id: String,
  
    val username: String,
  
  ) {
    
    
  }
      
        @kotlinx.serialization.Serializable
  public data class ExpenseProvidersOnExpenseItem(
  
    val shareAmount: Double?,
  
    val user: User,
  
  ) {
    
      
        @kotlinx.serialization.Serializable
  public data class User(
  
    val id: String,
  
    val username: String,
  
    val phone: String,
  
  ) {
    
    
  }
      
    
    
  }
      
    
    
  }
      
    
    
  }
  

  public companion object {
    public val operationName: String = "GetExpenseById"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun GetExpenseByIdQuery.ref(
  
    expenseId: java.util.UUID,

  
  
): com.google.firebase.dataconnect.QueryRef<
    GetExpenseByIdQuery.Data,
    GetExpenseByIdQuery.Variables
  > =
  ref(
    
      GetExpenseByIdQuery.Variables(
        expenseId=expenseId,
  
      )
    
  )

public suspend fun GetExpenseByIdQuery.execute(

  
    
      expenseId: java.util.UUID,
  fetchPolicy: com.google.firebase.dataconnect.QueryRef.FetchPolicy = com.google.firebase.dataconnect.QueryRef.FetchPolicy.PREFER_CACHE,
  

  ): com.google.firebase.dataconnect.QueryResult<
    GetExpenseByIdQuery.Data,
    GetExpenseByIdQuery.Variables
  > =
  ref(
    
      expenseId=expenseId,
  
    
  ).execute(fetchPolicy = fetchPolicy)


  public fun GetExpenseByIdQuery.flow(
    
      expenseId: java.util.UUID,

  
    
    ): kotlinx.coroutines.flow.Flow<GetExpenseByIdQuery.Data> =
    ref(
        
          expenseId=expenseId,
  
        
      ).subscribe()
      .flow
      ._flow_map { querySubscriptionResult -> querySubscriptionResult.result.getOrNull() }
      ._flow_filterNotNull()
      ._flow_map { it.data }

