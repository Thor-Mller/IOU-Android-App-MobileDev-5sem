
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


public interface SearchUsersInGroupQuery :
    GeneratedQuery<
      ExampleConnector,
      SearchUsersInGroupQuery.Data,
      SearchUsersInGroupQuery.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val groupId: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
  
    val search: String,
  
  ) {
    
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val groupUsers: List<GroupUsersItem>,
  
  ) {
    
      
        @kotlinx.serialization.Serializable
  public data class GroupUsersItem(
  
    val user: User,
  
  ) {
    
      
        @kotlinx.serialization.Serializable
  public data class User(
  
    val id: String,
  
    val username: String,
  
    val email: String,
  
    val phone: String,
  
    val totalMissing: Double,
  
    val totalDebt: Double,
  
  ) {
    
    
  }
      
    
    
  }
      
    
    
  }
  

  public companion object {
    public val operationName: String = "SearchUsersInGroup"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun SearchUsersInGroupQuery.ref(
  
    groupId: java.util.UUID,search: String,

  
  
): com.google.firebase.dataconnect.QueryRef<
    SearchUsersInGroupQuery.Data,
    SearchUsersInGroupQuery.Variables
  > =
  ref(
    
      SearchUsersInGroupQuery.Variables(
        groupId=groupId,search=search,
  
      )
    
  )

public suspend fun SearchUsersInGroupQuery.execute(

  
    
      groupId: java.util.UUID,search: String,
  fetchPolicy: com.google.firebase.dataconnect.QueryRef.FetchPolicy = com.google.firebase.dataconnect.QueryRef.FetchPolicy.PREFER_CACHE,
  

  ): com.google.firebase.dataconnect.QueryResult<
    SearchUsersInGroupQuery.Data,
    SearchUsersInGroupQuery.Variables
  > =
  ref(
    
      groupId=groupId,search=search,
  
    
  ).execute(fetchPolicy = fetchPolicy)


  public fun SearchUsersInGroupQuery.flow(
    
      groupId: java.util.UUID,search: String,

  
    
    ): kotlinx.coroutines.flow.Flow<SearchUsersInGroupQuery.Data> =
    ref(
        
          groupId=groupId,search=search,
  
        
      ).subscribe()
      .flow
      ._flow_map { querySubscriptionResult -> querySubscriptionResult.result.getOrNull() }
      ._flow_filterNotNull()
      ._flow_map { it.data }

