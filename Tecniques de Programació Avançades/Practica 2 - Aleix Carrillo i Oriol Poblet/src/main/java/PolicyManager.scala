trait PolicyManager[U,V]{
        def valorsInvoker(invokers:List[Invoker[U, V]], requiredMemory:Int):Invoker[U,V]
}
