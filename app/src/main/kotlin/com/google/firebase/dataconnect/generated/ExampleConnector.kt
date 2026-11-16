
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
  
    public val createCurrentUser: CreateCurrentUserMutation
  
    public val createExpense: CreateExpenseMutation
  
    public val deleteExpense: DeleteExpenseMutation
  
    public val findUser: FindUserQuery
  
    public val getExpenseById: GetExpenseByIdQuery
  
    public val getMyProfileAndExpenses: GetMyProfileAndExpensesQuery
  
    public val listUsers: ListUsersQuery
  
    public val settleUserShare: SettleUserShareMutation
  

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
  
    override val createCurrentUser by lazy(LazyThreadSafetyMode.PUBLICATION) {
      CreateCurrentUserMutationImpl(this)
    }
  
    override val createExpense by lazy(LazyThreadSafetyMode.PUBLICATION) {
      CreateExpenseMutationImpl(this)
    }
  
    override val deleteExpense by lazy(LazyThreadSafetyMode.PUBLICATION) {
      DeleteExpenseMutationImpl(this)
    }
  
    override val findUser by lazy(LazyThreadSafetyMode.PUBLICATION) {
      FindUserQueryImpl(this)
    }
  
    override val getExpenseById by lazy(LazyThreadSafetyMode.PUBLICATION) {
      GetExpenseByIdQueryImpl(this)
    }
  
    override val getMyProfileAndExpenses by lazy(LazyThreadSafetyMode.PUBLICATION) {
      GetMyProfileAndExpensesQueryImpl(this)
    }
  
    override val listUsers by lazy(LazyThreadSafetyMode.PUBLICATION) {
      ListUsersQueryImpl(this)
    }
  
    override val settleUserShare by lazy(LazyThreadSafetyMode.PUBLICATION) {
      SettleUserShareMutationImpl(this)
    }
  

  @com.google.firebase.dataconnect.ExperimentalFirebaseDataConnect
  override fun operations(): List<com.google.firebase.dataconnect.generated.GeneratedOperation<ExampleConnector, *, *>> =
    queries() + mutations()

  @com.google.firebase.dataconnect.ExperimentalFirebaseDataConnect
  override fun mutations(): List<com.google.firebase.dataconnect.generated.GeneratedMutation<ExampleConnector, *, *>> =
    listOf(
      addDebtorToExpense,
        createCurrentUser,
        createExpense,
        deleteExpense,
        settleUserShare,
        
    )

  @com.google.firebase.dataconnect.ExperimentalFirebaseDataConnect
  override fun queries(): List<com.google.firebase.dataconnect.generated.GeneratedQuery<ExampleConnector, *, *>> =
    listOf(
      findUser,
        getExpenseById,
        getMyProfileAndExpenses,
        listUsers,
        
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


private class SettleUserShareMutationImpl(
  connector: ExampleConnector
):
  SettleUserShareMutation,
  ExampleConnectorGeneratedMutationImpl<
      SettleUserShareMutation.Data,
      SettleUserShareMutation.Variables
  >(
    connector,
    SettleUserShareMutation.Companion.operationName,
    SettleUserShareMutation.Companion.dataDeserializer,
    SettleUserShareMutation.Companion.variablesSerializer,
  )


