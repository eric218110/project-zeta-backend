package com.eric218110.project.zeta.domain.usecases.card;

import com.eric218110.project.zeta.domain.http.card.AddCardRequest;
import com.eric218110.project.zeta.domain.http.card.ShowCardResponse;

public interface AddOneCard {
  ShowCardResponse addCard(AddCardRequest addCardDto);
}
