package com.frogwallet.help.utils;

import java.math.BigDecimal;

/**
 * Created by: Yumira.
 * Created on: 2018/12/8-下午22:01.
 * Description: 以太坊进制转换工具
 */
public final class ConvertUtil {

    private ConvertUtil() {
    }

    /**
     * wei 转 eth 示例
     * BigDecimal banlance = Convert.fromWei(ethGetBalance.getBalance().toString(), ConvertUtil.Unit.ETHER);
     */
    public static BigDecimal fromWei(String number, Unit unit) {
        return fromWei(new BigDecimal(number), unit);
    }

    public static BigDecimal fromWei(BigDecimal number, Unit unit) {
        return number.divide(unit.getWeiFactor());
    }

    /**
     * eth 转 wei 示例
     * BigInteger balance = Convert.toWei("0.9999999", ConvertUtil.Unit.ETHER).toBigInteger();
     */
    public static BigDecimal toWei(String number, Unit unit) {
        return toWei(new BigDecimal(number), unit);
    }

    public static BigDecimal toWei(BigDecimal number, Unit unit) {
        return number.multiply(unit.getWeiFactor());
    }

    public static enum Unit {
        WEI("wei", 0),
        KWEI("kwei", 3),
        MWEI("mwei", 6),
        GWEI("gwei", 9),
        SZABO("szabo", 12),
        FINNEY("finney", 15),
        ETHER("ether", 18),
        KETHER("kether", 21),
        METHER("mether", 24),
        GETHER("gether", 27);

        private String name;
        private BigDecimal weiFactor;

        private Unit(String name, int factor) {
            this.name = name;
            this.weiFactor = BigDecimal.TEN.pow(factor);
        }

        public BigDecimal getWeiFactor() {
            return this.weiFactor;
        }

        public String toString() {
            return this.name;
        }

        public static Unit fromString(String name) {
            if (name != null) {
                Unit[] var1 = values();
                int var2 = var1.length;

                for(int var3 = 0; var3 < var2; ++var3) {
                    Unit unit = var1[var3];
                    if (name.equalsIgnoreCase(unit.name)) {
                        return unit;
                    }
                }
            }

            return valueOf(name);
        }
    }
}