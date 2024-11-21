package com.kkimleang.rrms.payload.response.property;

import com.kkimleang.rrms.entity.*;
import java.io.*;
import java.util.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CharacteristicResponse implements Serializable {
    private UUID id;
    private String name;
    private String description;

    public static Set<CharacteristicResponse> fromCharacteristics(Set<PropertyCharacteristic> characteristics) {
        return characteristics.stream().map(characteristic -> {
            CharacteristicResponse characteristicResponse = new CharacteristicResponse();
            characteristicResponse.setId(characteristic.getId());
            characteristicResponse.setName(characteristic.getName());
            characteristicResponse.setDescription(characteristic.getDescription());
            return characteristicResponse;
        }).collect(java.util.stream.Collectors.toSet());
    }
}
