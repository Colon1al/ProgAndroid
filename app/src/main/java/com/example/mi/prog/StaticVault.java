package com.example.mi.prog;

public class StaticVault extends Vault {
public static Vault SVault = new Vault();
    @Override
    public void SetSVault(Vault v)
    {
        StaticVault.SVault =v;
    }
    @Override
    public Vault GetSVault()
    {
        return StaticVault.SVault;
    }
}
