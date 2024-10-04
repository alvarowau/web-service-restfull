package org.alvarowau.webservicerestfull.model;

public abstract class Base {

    private Long id;

    public Base() {
    }

    public Base(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Base{" +
                "id=" + id +
                '}';
    }
}
