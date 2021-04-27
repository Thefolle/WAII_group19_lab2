# Whiteboard

## High-level model

```plantuml
@startuml

class User {
    username
    email
    password
    roles
    isEnabled
}
class Customer {
    name
    surname
    deliveryAddress
    email
}
class Wallet {
    balance
}
class Transaction {
    transactedMoneyAmount
    timestamp
}

User -- Customer
Customer -- "0, *"  Wallet
Wallet "2" -- "0, *" Transaction

note "Each entity must have a surrogate integer key that here is not explicitly shown" as N1
note right of Customer: the email is unique

@enduml
```

## Low-level model

```plantuml
@startuml

class User {
    username
    email
    password
    roles
    isEnabled
}
class Customer {
    name
    surname
    deliveryAddress
    email
    user: User
}
class Wallet {
    balance
    customer: Customer
}
class Transaction {
    debtor: Wallet
    creditor: Wallet
    transactedMoneyAmount
    timestamp
}

User <-- Customer
Customer <-- "0, *"  Wallet
Wallet "2" <-- "0, *" Transaction

note "Each entity must have a surrogate integer key that here is not explicitly shown" as N1
note right of Customer: the email is unique

@enduml
```
