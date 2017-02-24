package com.aude.sharding.router.strategy.function;

/**
 * Created by sidawei on 16/4/2.
 *
 * suffix: [0, 8, 16, 24, 32, 40, 48, 56, 64, 72, 80, 88, 96, 104, 112, 120]
 *
 * @See VirtualModFunction
 */
public class VirtualModFunction128_16 extends VirtualModFunction{

    public VirtualModFunction128_16(){
        super(128, 16);
    }
}
