package com.capitole.inditex.application.ports.input;

public interface GeneralUseCase<A, R>{

  R execute(A input);
}
