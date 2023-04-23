package com.warrior.luminate.enums;

/**
 * 调度过期策略
 *
 * @author 3y
 */
public enum MisfireStrategyEnum {

    /**
     * do nothing
     */
    DO_NOTHING,

    /**
     * fire once now
     */
    FIRE_ONCE_NOW;

    MisfireStrategyEnum() {
    }
}
