
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

import com.google.firebase.dataconnect.getInstance as _fdcGetInstance
import kotlin.time.Duration.Companion.milliseconds as _milliseconds

public interface ExampleConnector : com.google.firebase.dataconnect.generated.GeneratedConnector<ExampleConnector> {
  override val dataConnect: com.google.firebase.dataconnect.FirebaseDataConnect

  
    public val addDebtorToExpense: AddDebtorToExpenseMutation
  
    public val addUserToGroup: AddUserToGroupMutation
  
    public val createCurrentUser: CreateCurrentUserMutation
  
    public val createExpense: CreateExpenseMutation
  
    public val createGroup: CreateGroupMutation
  
    public val deleteCurrentUser: DeleteCurrentUserMutation
  
    public val deleteExpense: DeleteExpenseMutation
  
    public val deleteGroup: DeleteGroupMutation
  
    public val findUser: FindUserQuery
  
    public val getCurrentUser: GetCurrentUserQuery
  
    public val getExpenseById: GetExpenseByIdQuery
  
    public val getMyProfileAndExpenses: GetMyProfileAndExpensesQuery
  
    public val getUsersInGroup: GetUsersInGroupQuery
  
    public val listUsers: ListUsersQuery
  
    public val removeDebtorFromExpense: RemoveDebtorFromExpenseMutation
  
    public val removeUserFromGroup: RemoveUserFromGroupMutation
  
    public val searchUsersInGroup: SearchUsersInGroupQuery
  
    public val updateGroup: UpdateGroupMutation
  

  public companion object {
    @Suppress("MemberVisibilityCanBePrivate")
    public val config: com.google.firebase.dataconnect.ConnectorConfig = com.google.firebase.dataconnect.ConnectorConfig(
      connector = "example",
      location = "europe-west4",
      serviceId = "iou-android-app-mobiledev-5sem",
    )

    public fun getInstance(
      dataConnect: com.google.firebase.dataconnect.FirebaseDataConnect
    ):ExampleConnector = synchronized(instances) {
      instances.getOrPut(dataConnect) {
        ExampleConnectorImpl(dataConnect)
      }
    }

    private val instances = java.util.WeakHashMap<com.google.firebase.dataconnect.FirebaseDataConnect, ExampleConnectorImpl>()

    
    public val defaultCacheSettings: com.google.firebase.dataconnect.CacheSettings =
      com.google.firebase.dataconnect.CacheSettings(
        
        
      )

    public val defaultDataConnectSettings: com.google.firebase.dataconnect.DataConnectSettings =
      com.google.firebase.dataconnect.DataConnectSettings(
        cacheSettings = defaultCacheSettings,
      )
    
  }
}

public val ExampleConnector.Companion.instance:ExampleConnector
  get() = getInstance(com.google.firebase.dataconnect.FirebaseDataConnect._fdcGetInstance(
    config, defaultDataConnectSettings
  ))

public fun ExampleConnector.Companion.getInstance(
  settings: com.google.firebase.dataconnect.DataConnectSettings = defaultDataConnectSettings
):ExampleConnector =
  getInstance(com.google.firebase.dataconnect.FirebaseDataConnect._fdcGetInstance(config, settings))

public fun ExampleConnector.Companion.getInstance(
  app: com.google.firebase.FirebaseApp,
  settings: com.google.firebase.dataconnect.DataConnectSettings = defaultDataConnectSettings
):ExampleConnector =
  getInstance(com.google.firebase.dataconnect.FirebaseDataConnect._fdcGetInstance(app, config, settings))

private class ExampleConnectorImpl(
  override val dataConnect: com.google.firebase.dataconnect.FirebaseDataConnect
) : ExampleConnector {
  
    override val addDebtorToExpense by lazy(LazyThreadSafetyMode.PUBLICATION) {
      AddDebtorToExpenseMutationImpl(this)
    }
  
    override val addUserToGroup by lazy(LazyThreadSafetyMode.PUBLICATION) {
      AddUserToGroupMutationImpl(this)
    }
  
    override val createCurrentUser by lazy(LazyThreadSafetyMode.PUBLICATION) {
      CreateCurrentUserMutationImpl(this)
    }
  
    override val createExpense by lazy(LazyThreadSafetyMode.PUBLICATION) {
      CreateExpenseMutationImpl(this)
    }
  
    override val createGroup by lazy(LazyThreadSafetyMode.PUBLICATION) {
      CreateGroupMutationImpl(this)
    }
  
    override val deleteCurrentUser by lazy(LazyThreadSafetyMode.PUBLICATION) {
      DeleteCurrentUserMutationImpl(this)
    }
  
    override val deleteExpense by lazy(LazyThreadSafetyMode.PUBLICATION) {
      DeleteExpenseMutationImpl(this)
    }
  
    override val deleteGroup by lazy(LazyThreadSafetyMode.PUBLICATION) {
      DeleteGroupMutationImpl(this)
    }
  
    override val findUser by lazy(LazyThreadSafetyMode.PUBLICATION) {
      FindUserQueryImpl(this)
    }
  
    override val getCurrentUser by lazy(LazyThreadSafetyMode.PUBLICATION) {
      GetCurrentUserQueryImpl(this)
    }
  
    override val getExpenseById by lazy(LazyThreadSafetyMode.PUBLICATION) {
      GetExpenseByIdQueryImpl(this)
    }
  
    override val getMyProfileAndExpenses by lazy(LazyThreadSafetyMode.PUBLICATION) {
      GetMyProfileAndExpensesQueryImpl(this)
    }
  
    override val getUsersInGroup by lazy(LazyThreadSafetyMode.PUBLICATION) {
      GetUsersInGroupQueryImpl(this)
    }
  
    override val listUsers by lazy(LazyThreadSafetyMode.PUBLICATION) {
      ListUsersQueryImpl(this)
    }
  
    override val removeDebtorFromExpense by lazy(LazyThreadSafetyMode.PUBLICATION) {
      RemoveDebtorFromExpenseMutationImpl(this)
    }
  
    override val removeUserFromGroup by lazy(LazyThreadSafetyMode.PUBLICATION) {
      RemoveUserFromGroupMutationImpl(this)
    }
  
    override val searchUsersInGroup by lazy(LazyThreadSafetyMode.PUBLICATION) {
      SearchUsersInGroupQueryImpl(this)
    }
  
    override val updateGroup by lazy(LazyThreadSafetyMode.PUBLICATION) {
      UpdateGroupMutationImpl(this)
    }
  

  @com.google.firebase.dataconnect.ExperimentalFirebaseDataConnect
  override fun operations(): List<com.google.firebase.dataconnect.generated.GeneratedOperation<ExampleConnector, *, *>> =
    queries() + mutations()

  @com.google.firebase.dataconnect.ExperimentalFirebaseDataConnect
  override fun mutations(): List<com.google.firebase.dataconnect.generated.GeneratedMutation<ExampleConnector, *, *>> =
    listOf(
      addDebtorToExpense,
        addUserToGroup,
        createCurrentUser,
        createExpense,
        createGroup,
        deleteCurrentUser,
        deleteExpense,
        deleteGroup,
        removeDebtorFromExpense,
        removeUserFromGroup,
        updateGroup,
        
    )

  @com.google.firebase.dataconnect.ExperimentalFirebaseDataConnect
  override fun queries(): List<com.google.firebase.dataconnect.generated.GeneratedQuery<ExampleConnector, *, *>> =
    listOf(
      findUser,
        getCurrentUser,
        getExpenseById,
        getMyProfileAndExpenses,
        getUsersInGroup,
        listUsers,
        searchUsersInGroup,
        
    )

  @com.google.firebase.dataconnect.ExperimentalFirebaseDataConnect
  override fun copy(dataConnect: com.google.firebase.dataconnect.FirebaseDataConnect) =
    ExampleConnectorImpl(dataConnect)

  override fun equals(other: Any?): Boolean =
    other is ExampleConnectorImpl &&
    other.dataConnect == dataConnect

  override fun hashCode(): Int =
    java.util.Objects.hash(
      "ExampleConnectorImpl",
      dataConnect,
    )

  override fun toString(): String =
    "ExampleConnectorImpl(dataConnect=$dataConnect)"
}



private open class ExampleConnectorGeneratedQueryImpl<Data, Variables>(
  override val connector: ExampleConnector,
  override val operationName: String,
  override val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data>,
  override val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables>,
) : com.google.firebase.dataconnect.generated.GeneratedQuery<ExampleConnector, Data, Variables> {

  @com.google.firebase.dataconnect.ExperimentalFirebaseDataConnect
  override fun copy(
    connector: ExampleConnector,
    operationName: String,
    dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data>,
    variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables>,
  ) =
    ExampleConnectorGeneratedQueryImpl(
      connector, operationName, dataDeserializer, variablesSerializer
    )

  @com.google.firebase.dataconnect.ExperimentalFirebaseDataConnect
  override fun <NewVariables> withVariablesSerializer(
    variablesSerializer: kotlinx.serialization.SerializationStrategy<NewVariables>
  ) =
    ExampleConnectorGeneratedQueryImpl(
      connector, operationName, dataDeserializer, variablesSerializer
    )

  @com.google.firebase.dataconnect.ExperimentalFirebaseDataConnect
  override fun <NewData> withDataDeserializer(
    dataDeserializer: kotlinx.serialization.DeserializationStrategy<NewData>
  ) =
    ExampleConnectorGeneratedQueryImpl(
      connector, operationName, dataDeserializer, variablesSerializer
    )

  override fun equals(other: Any?): Boolean =
    other is ExampleConnectorGeneratedQueryImpl<*,*> &&
    other.connector == connector &&
    other.operationName == operationName &&
    other.dataDeserializer == dataDeserializer &&
    other.variablesSerializer == variablesSerializer

  override fun hashCode(): Int =
    java.util.Objects.hash(
      "ExampleConnectorGeneratedQueryImpl",
      connector, operationName, dataDeserializer, variablesSerializer
    )

  override fun toString(): String =
    "ExampleConnectorGeneratedQueryImpl(" +
    "operationName=$operationName, " +
    "dataDeserializer=$dataDeserializer, " +
    "variablesSerializer=$variablesSerializer, " +
    "connector=$connector)"
}

private open class ExampleConnectorGeneratedMutationImpl<Data, Variables>(
  override val connector: ExampleConnector,
  override val operationName: String,
  override val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data>,
  override val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables>,
) : com.google.firebase.dataconnect.generated.GeneratedMutation<ExampleConnector, Data, Variables> {

  @com.google.firebase.dataconnect.ExperimentalFirebaseDataConnect
  override fun copy(
    connector: ExampleConnector,
    operationName: String,
    dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data>,
    variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables>,
  ) =
    ExampleConnectorGeneratedMutationImpl(
      connector, operationName, dataDeserializer, variablesSerializer
    )

  @com.google.firebase.dataconnect.ExperimentalFirebaseDataConnect
  override fun <NewVariables> withVariablesSerializer(
    variablesSerializer: kotlinx.serialization.SerializationStrategy<NewVariables>
  ) =
    ExampleConnectorGeneratedMutationImpl(
      connector, operationName, dataDeserializer, variablesSerializer
    )

  @com.google.firebase.dataconnect.ExperimentalFirebaseDataConnect
  override fun <NewData> withDataDeserializer(
    dataDeserializer: kotlinx.serialization.DeserializationStrategy<NewData>
  ) =
    ExampleConnectorGeneratedMutationImpl(
      connector, operationName, dataDeserializer, variablesSerializer
    )

  override fun equals(other: Any?): Boolean =
    other is ExampleConnectorGeneratedMutationImpl<*,*> &&
    other.connector == connector &&
    other.operationName == operationName &&
    other.dataDeserializer == dataDeserializer &&
    other.variablesSerializer == variablesSerializer

  override fun hashCode(): Int =
    java.util.Objects.hash(
      "ExampleConnectorGeneratedMutationImpl",
      connector, operationName, dataDeserializer, variablesSerializer
    )

  override fun toString(): String =
    "ExampleConnectorGeneratedMutationImpl(" +
    "operationName=$operationName, " +
    "dataDeserializer=$dataDeserializer, " +
    "variablesSerializer=$variablesSerializer, " +
    "connector=$connector)"
}



private class AddDebtorToExpenseMutationImpl(
  connector: ExampleConnector
):
  AddDebtorToExpenseMutation,
  ExampleConnectorGeneratedMutationImpl<
      AddDebtorToExpenseMutation.Data,
      AddDebtorToExpenseMutation.Variables
  >(
    connector,
    AddDebtorToExpenseMutation.Companion.operationName,
    AddDebtorToExpenseMutation.Companion.dataDeserializer,
    AddDebtorToExpenseMutation.Companion.variablesSerializer,
  )


private class AddUserToGroupMutationImpl(
  connector: ExampleConnector
):
  AddUserToGroupMutation,
  ExampleConnectorGeneratedMutationImpl<
      AddUserToGroupMutation.Data,
      AddUserToGroupMutation.Variables
  >(
    connector,
    AddUserToGroupMutation.Companion.operationName,
    AddUserToGroupMutation.Companion.dataDeserializer,
    AddUserToGroupMutation.Companion.variablesSerializer,
  )


private class CreateCurrentUserMutationImpl(
  connector: ExampleConnector
):
  CreateCurrentUserMutation,
  ExampleConnectorGeneratedMutationImpl<
      CreateCurrentUserMutation.Data,
      CreateCurrentUserMutation.Variables
  >(
    connector,
    CreateCurrentUserMutation.Companion.operationName,
    CreateCurrentUserMutation.Companion.dataDeserializer,
    CreateCurrentUserMutation.Companion.variablesSerializer,
  )


private class CreateExpenseMutationImpl(
  connector: ExampleConnector
):
  CreateExpenseMutation,
  ExampleConnectorGeneratedMutationImpl<
      CreateExpenseMutation.Data,
      CreateExpenseMutation.Variables
  >(
    connector,
    CreateExpenseMutation.Companion.operationName,
    CreateExpenseMutation.Companion.dataDeserializer,
    CreateExpenseMutation.Companion.variablesSerializer,
  )


private class CreateGroupMutationImpl(
  connector: ExampleConnector
):
  CreateGroupMutation,
  ExampleConnectorGeneratedMutationImpl<
      CreateGroupMutation.Data,
      CreateGroupMutation.Variables
  >(
    connector,
    CreateGroupMutation.Companion.operationName,
    CreateGroupMutation.Companion.dataDeserializer,
    CreateGroupMutation.Companion.variablesSerializer,
  )


private class DeleteCurrentUserMutationImpl(
  connector: ExampleConnector
):
  DeleteCurrentUserMutation,
  ExampleConnectorGeneratedMutationImpl<
      DeleteCurrentUserMutation.Data,
      Unit
  >(
    connector,
    DeleteCurrentUserMutation.Companion.operationName,
    DeleteCurrentUserMutation.Companion.dataDeserializer,
    DeleteCurrentUserMutation.Companion.variablesSerializer,
  )


private class DeleteExpenseMutationImpl(
  connector: ExampleConnector
):
  DeleteExpenseMutation,
  ExampleConnectorGeneratedMutationImpl<
      DeleteExpenseMutation.Data,
      DeleteExpenseMutation.Variables
  >(
    connector,
    DeleteExpenseMutation.Companion.operationName,
    DeleteExpenseMutation.Companion.dataDeserializer,
    DeleteExpenseMutation.Companion.variablesSerializer,
  )


private class DeleteGroupMutationImpl(
  connector: ExampleConnector
):
  DeleteGroupMutation,
  ExampleConnectorGeneratedMutationImpl<
      DeleteGroupMutation.Data,
      DeleteGroupMutation.Variables
  >(
    connector,
    DeleteGroupMutation.Companion.operationName,
    DeleteGroupMutation.Companion.dataDeserializer,
    DeleteGroupMutation.Companion.variablesSerializer,
  )


private class FindUserQueryImpl(
  connector: ExampleConnector
):
  FindUserQuery,
  ExampleConnectorGeneratedQueryImpl<
      FindUserQuery.Data,
      FindUserQuery.Variables
  >(
    connector,
    FindUserQuery.Companion.operationName,
    FindUserQuery.Companion.dataDeserializer,
    FindUserQuery.Companion.variablesSerializer,
  )


private class GetCurrentUserQueryImpl(
  connector: ExampleConnector
):
  GetCurrentUserQuery,
  ExampleConnectorGeneratedQueryImpl<
      GetCurrentUserQuery.Data,
      Unit
  >(
    connector,
    GetCurrentUserQuery.Companion.operationName,
    GetCurrentUserQuery.Companion.dataDeserializer,
    GetCurrentUserQuery.Companion.variablesSerializer,
  )


private class GetExpenseByIdQueryImpl(
  connector: ExampleConnector
):
  GetExpenseByIdQuery,
  ExampleConnectorGeneratedQueryImpl<
      GetExpenseByIdQuery.Data,
      GetExpenseByIdQuery.Variables
  >(
    connector,
    GetExpenseByIdQuery.Companion.operationName,
    GetExpenseByIdQuery.Companion.dataDeserializer,
    GetExpenseByIdQuery.Companion.variablesSerializer,
  )


private class GetMyProfileAndExpensesQueryImpl(
  connector: ExampleConnector
):
  GetMyProfileAndExpensesQuery,
  ExampleConnectorGeneratedQueryImpl<
      GetMyProfileAndExpensesQuery.Data,
      Unit
  >(
    connector,
    GetMyProfileAndExpensesQuery.Companion.operationName,
    GetMyProfileAndExpensesQuery.Companion.dataDeserializer,
    GetMyProfileAndExpensesQuery.Companion.variablesSerializer,
  )


private class GetUsersInGroupQueryImpl(
  connector: ExampleConnector
):
  GetUsersInGroupQuery,
  ExampleConnectorGeneratedQueryImpl<
      GetUsersInGroupQuery.Data,
      GetUsersInGroupQuery.Variables
  >(
    connector,
    GetUsersInGroupQuery.Companion.operationName,
    GetUsersInGroupQuery.Companion.dataDeserializer,
    GetUsersInGroupQuery.Companion.variablesSerializer,
  )


private class ListUsersQueryImpl(
  connector: ExampleConnector
):
  ListUsersQuery,
  ExampleConnectorGeneratedQueryImpl<
      ListUsersQuery.Data,
      Unit
  >(
    connector,
    ListUsersQuery.Companion.operationName,
    ListUsersQuery.Companion.dataDeserializer,
    ListUsersQuery.Companion.variablesSerializer,
  )


private class RemoveDebtorFromExpenseMutationImpl(
  connector: ExampleConnector
):
  RemoveDebtorFromExpenseMutation,
  ExampleConnectorGeneratedMutationImpl<
      RemoveDebtorFromExpenseMutation.Data,
      RemoveDebtorFromExpenseMutation.Variables
  >(
    connector,
    RemoveDebtorFromExpenseMutation.Companion.operationName,
    RemoveDebtorFromExpenseMutation.Companion.dataDeserializer,
    RemoveDebtorFromExpenseMutation.Companion.variablesSerializer,
  )


private class RemoveUserFromGroupMutationImpl(
  connector: ExampleConnector
):
  RemoveUserFromGroupMutation,
  ExampleConnectorGeneratedMutationImpl<
      RemoveUserFromGroupMutation.Data,
      RemoveUserFromGroupMutation.Variables
  >(
    connector,
    RemoveUserFromGroupMutation.Companion.operationName,
    RemoveUserFromGroupMutation.Companion.dataDeserializer,
    RemoveUserFromGroupMutation.Companion.variablesSerializer,
  )


private class SearchUsersInGroupQueryImpl(
  connector: ExampleConnector
):
  SearchUsersInGroupQuery,
  ExampleConnectorGeneratedQueryImpl<
      SearchUsersInGroupQuery.Data,
      SearchUsersInGroupQuery.Variables
  >(
    connector,
    SearchUsersInGroupQuery.Companion.operationName,
    SearchUsersInGroupQuery.Companion.dataDeserializer,
    SearchUsersInGroupQuery.Companion.variablesSerializer,
  )


private class UpdateGroupMutationImpl(
  connector: ExampleConnector
):
  UpdateGroupMutation,
  ExampleConnectorGeneratedMutationImpl<
      UpdateGroupMutation.Data,
      UpdateGroupMutation.Variables
  >(
    connector,
    UpdateGroupMutation.Companion.operationName,
    UpdateGroupMutation.Companion.dataDeserializer,
    UpdateGroupMutation.Companion.variablesSerializer,
  )


