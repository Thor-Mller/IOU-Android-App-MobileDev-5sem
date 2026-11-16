
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


public interface FindUserQuery :
    com.google.firebase.dataconnect.generated.GeneratedQuery<
      ExampleConnector,
      FindUserQuery.Data,
      FindUserQuery.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val search: String,
  
  ) {
    
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val users: List<UsersItem>,
  
  ) {
    
      
        @kotlinx.serialization.Serializable
  public data class UsersItem(
  
    val id: String,
  
    val username: String,
  
    val phone: String,
  
  ) {
    
    
  }
      
    
    
  }
  

  public companion object {
    public val operationName: String = "FindUser"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun FindUserQuery.ref(
  
    search: String,

  
  
): com.google.firebase.dataconnect.QueryRef<
    FindUserQuery.Data,
    FindUserQuery.Variables
  > =
  ref(
    
      FindUserQuery.Variables(
        search=search,
  
      )
    
  )

public suspend fun FindUserQuery.execute(

  
    
      search: String,
  fetchPolicy: com.google.firebase.dataconnect.QueryRef.FetchPolicy = com.google.firebase.dataconnect.QueryRef.FetchPolicy.PREFER_CACHE,
  

  ): com.google.firebase.dataconnect.QueryResult<
    FindUserQuery.Data,
    FindUserQuery.Variables
  > =
  ref(
    
      search=search,
  
    
  ).execute(fetchPolicy = fetchPolicy)


  public fun FindUserQuery.flow(
    
      search: String,

  
    
    ): kotlinx.coroutines.flow.Flow<FindUserQuery.Data> =
    ref(
        
          search=search,
  
        
      ).subscribe()
      .flow
      ._flow_map { querySubscriptionResult -> querySubscriptionResult.result.getOrNull() }
      ._flow_filterNotNull()
      ._flow_map { it.data }

