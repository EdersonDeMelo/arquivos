package com.sistran.fastclaims.application;

public abstract class CustomUseCase<IN_ONE, IN_TWO> {

    public abstract void executar(IN_ONE one, IN_TWO two);
}