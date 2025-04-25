package com.chuppch.api;

import com.chuppch.api.dto.LockMarketPayOrderRequestDTO;
import com.chuppch.api.dto.LockMarketPayOrderResponseDTO;
import com.chuppch.api.response.Response;

public interface IMarketTradeService {

    Response<LockMarketPayOrderResponseDTO> lockMarketPayOrder(LockMarketPayOrderRequestDTO lockMarketPayOrderRequestDTO);

}
