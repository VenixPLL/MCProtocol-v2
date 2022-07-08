/*
 * MCProtocol-v2
 * Copyright (C) 2022.  VenixPLL
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package me.dickmeister.mcprotocol.network.packet.impl.login.client;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.dickmeister.mcprotocol.network.ConnectionState;
import me.dickmeister.mcprotocol.network.PacketDirection;
import me.dickmeister.mcprotocol.network.netty.PacketBuffer;
import me.dickmeister.mcprotocol.network.netty.encryption.CryptManager;
import me.dickmeister.mcprotocol.network.packet.Packet;

import javax.crypto.SecretKey;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Objects;

@Getter
@NoArgsConstructor
@Packet.PacketInfo(connectionState = ConnectionState.LOGIN, packetDirection = PacketDirection.SERVERBOUND)
public class ClientLoginEncryptionResponsePacket extends Packet {

    private byte[] secretKeyEncrypted = new byte[0];
    private byte[] verifyTokenEncrypted = new byte[0];

    {
        this.setId(0x01);
    }

    public ClientLoginEncryptionResponsePacket(final SecretKey secretKey, final PublicKey publicKey, final byte[] verifyToken) {
        secretKeyEncrypted = CryptManager.encryptData(publicKey, secretKey.getEncoded());
        verifyTokenEncrypted = CryptManager.encryptData(publicKey, verifyToken);
    }

    @Override
    public void write(PacketBuffer out) throws Exception {
        out.writeByteArray(secretKeyEncrypted);
        out.writeByteArray(verifyTokenEncrypted);
    }

    @Override
    public void read(PacketBuffer in) throws Exception {
        secretKeyEncrypted = in.readByteArray();
        verifyTokenEncrypted = in.readByteArray();
    }

    public SecretKey getSecretKey(final PrivateKey key) {
        return CryptManager.decryptSharedKey(key, secretKeyEncrypted);
    }

    public byte[] getVerifyToken(final PrivateKey key) {
        return Objects.isNull(key) ? verifyTokenEncrypted : CryptManager.decryptData(key, verifyTokenEncrypted);
    }
}
