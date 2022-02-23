package com.azienda.foodies.DTO;/*
 * File: PostDTO
 * Project: Foodies
 * File Created: 23/02/22 - 09:17
 * Author: Andrea Fucci (fucciandrea01@gmail.com)
 * Copyright © 2022-2022 Andrea Fucci
 */

import java.time.LocalDateTime;

public class PostDTO extends UtenteDTOLogin {

    private String titolo;
    private String descrizione;
    private LocalDateTime dataPubblicazione;
    private String immagine;
    private LocalDateTime lastUpdate;

    public PostDTO(String username, String password, String titolo, String descrizione, LocalDateTime dataPubblicazione, String immagine, LocalDateTime lastUpdate) {
        super(username, password);
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.dataPubblicazione = dataPubblicazione;
        this.immagine = immagine;
        this.lastUpdate = lastUpdate;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public LocalDateTime getDataPubblicazione() {
        return dataPubblicazione;
    }

    public void setDataPubblicazione(LocalDateTime dataPubblicazione) {
        this.dataPubblicazione = dataPubblicazione;
    }

    public String getImmagine() {
        return immagine;
    }

    public void setImmagine(String immagine) {
        this.immagine = immagine;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
