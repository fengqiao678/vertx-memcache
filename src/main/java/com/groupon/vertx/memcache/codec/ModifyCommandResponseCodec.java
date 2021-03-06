/**
 * Copyright 2014 Groupon.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.groupon.vertx.memcache.codec;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;
import io.vertx.core.eventbus.impl.CodecManager;
import io.vertx.core.json.JsonObject;

import com.groupon.vertx.memcache.client.JsendStatus;
import com.groupon.vertx.memcache.client.response.ModifyCommandResponse;

/**
 * A MessageCodec for passing the ModifyCommandResponse in Vertx
 *
 * @author Stuart Siegrist (fsiegrist at groupon dot com)
 * @since 3.1.0
 */
public class ModifyCommandResponseCodec implements MessageCodec<ModifyCommandResponse, ModifyCommandResponse> {

    @Override
    public void encodeToWire(Buffer buffer, ModifyCommandResponse commandResponse) {
        JsonObject json = new JsonObject();
        json.put("status", commandResponse.getStatus());
        json.put("message", commandResponse.getMessage());
        json.put("data", commandResponse.getData());

        CodecManager.JSON_OBJECT_MESSAGE_CODEC.encodeToWire(buffer, json);
    }

    @Override
    public ModifyCommandResponse decodeFromWire(int i, Buffer buffer) {
        JsonObject json = CodecManager.JSON_OBJECT_MESSAGE_CODEC.decodeFromWire(i, buffer);

        JsendStatus status = JsendStatus.valueOf(json.getString("status"));
        String message = json.getString("message");
        Integer data = json.getInteger("data");

        return new ModifyCommandResponse.Builder()
                .setStatus(status)
                .setMessage(message)
                .setData(data)
                .build();
    }

    @Override
    public ModifyCommandResponse transform(ModifyCommandResponse commandResponse) {
        return commandResponse;
    }

    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }

    @Override
    public byte systemCodecID() {
        return -1;
    }
}
