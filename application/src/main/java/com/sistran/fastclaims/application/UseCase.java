package com.sistran.fastclaims.application;

public abstract class UseCase<IN, OUT> {

    public abstract OUT executar(IN in);
}