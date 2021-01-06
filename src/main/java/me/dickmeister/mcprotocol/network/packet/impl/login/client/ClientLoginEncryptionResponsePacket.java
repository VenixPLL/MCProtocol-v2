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
