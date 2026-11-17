
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


public interface GetUsersInGroupQuery :
    com.google.firebase.dataconnect.generated.GeneratedQuery<
      ExampleConnector,
      GetUsersInGroupQuery.Data,
      GetUsersInGroupQuery.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val groupId: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
  
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
    public val operationName: String = "GetUsersInGroup"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun GetUsersInGroupQuery.ref(
  
    groupId: java.util.UUID,

  
  
): com.google.firebase.dataconnect.QueryRef<
    GetUsersInGroupQuery.Data,
    GetUsersInGroupQuery.Variables
  > =
  ref(
    
      GetUsersInGroupQuery.Variables(
        groupId=groupId,
  
      )
    
  )

public suspend fun GetUsersInGroupQuery.execute(

  
    
      groupId: java.util.UUID,
  fetchPolicy: com.google.firebase.dataconnect.QueryRef.FetchPolicy = com.google.firebase.dataconnect.QueryRef.FetchPolicy.PREFER_CACHE,
  

  ): com.google.firebase.dataconnect.QueryResult<
    GetUsersInGroupQuery.Data,
    GetUsersInGroupQuery.Variables
  > =
  ref(
    
      groupId=groupId,
  
    
  ).execute(fetchPolicy = fetchPolicy)


  public fun GetUsersInGroupQuery.flow(
    
      groupId: java.util.UUID,

  
    
    ): kotlinx.coroutines.flow.Flow<GetUsersInGroupQuery.Data> =
    ref(
        
          groupId=groupId,
  
        
      ).subscribe()
      .flow
      ._flow_map { querySubscriptionResult -> querySubscriptionResult.result.getOrNull() }
      ._flow_filterNotNull()
      ._flow_map { it.data }

