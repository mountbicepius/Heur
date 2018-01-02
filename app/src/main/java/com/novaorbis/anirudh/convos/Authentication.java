package com.novaorbis.anirudh.convos;

final class Authentication
{
    public byte unique ;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte getUnique() {
        return unique;
    }

    public void setUnique(byte unique) {
        this.unique = unique;
    }

    public Authentication(byte uId, String namId)
    {
        this.name = namId;
        this.unique =uId;
    }

}
