package com.serhiihurin.privattestapi5.facade.interfaces;

import com.serhiihurin.privattestapi5.dto.ClientDataDTO;
import com.serhiihurin.privattestapi5.entity.Client;

import java.util.List;

public interface DatabaseInfoSaverFacade {
    List<Client> getClients();
    void saveClientData(ClientDataDTO clientDataDTO);
}
