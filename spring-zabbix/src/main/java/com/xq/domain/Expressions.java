package com.xq.domain;

public class Expressions {
    private Long expressionid;

    private Long regexpid;

    private String expression;

    private Integer expressionType;

    private String expDelimiter;

    private Integer caseSensitive;

    public Long getExpressionid() {
        return expressionid;
    }

    public void setExpressionid(Long expressionid) {
        this.expressionid = expressionid;
    }

    public Long getRegexpid() {
        return regexpid;
    }

    public void setRegexpid(Long regexpid) {
        this.regexpid = regexpid;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public Integer getExpressionType() {
        return expressionType;
    }

    public void setExpressionType(Integer expressionType) {
        this.expressionType = expressionType;
    }

    public String getExpDelimiter() {
        return expDelimiter;
    }

    public void setExpDelimiter(String expDelimiter) {
        this.expDelimiter = expDelimiter;
    }

    public Integer getCaseSensitive() {
        return caseSensitive;
    }

    public void setCaseSensitive(Integer caseSensitive) {
        this.caseSensitive = caseSensitive;
    }
}