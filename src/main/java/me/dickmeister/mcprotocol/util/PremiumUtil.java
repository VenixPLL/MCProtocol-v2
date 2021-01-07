package me.dickmeister.mcprotocol.util;

import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.dickmeister.mcprotocol.MCProtocol;
import me.dickmeister.mcprotocol.network.netty.encryption.CryptManager;
import me.dickmeister.mcprotocol.network.objects.Session;
import me.dickmeister.mcprotocol.network.packet.impl.login.client.ClientLoginEncryptionResponsePacket;
import me.dickmeister.mcprotocol.network.packet.impl.login.server.ServerLoginEncryptionRequestPacket;
import net.chris54721.openmcauthenticator.OpenMCAuthenticator;
import net.chris54721.openmcauthenticator.exceptions.AuthenticationUnavailableException;
import net.chris54721.openmcauthenticator.exceptions.RequestException;
import net.chris54721.openmcauthenticator.responses.AuthenticationResponse;

import javax.crypto.SecretKey;
import javax.naming.AuthenticationException;
import java.math.BigInteger;
import java.net.Proxy;
import java.security.PublicKey;
import java.util.Objects;
import java.util.UUID;

public class PremiumUtil {

    public PremiumUtil() {
    }

    /**
     * Parsing client encryption. Enabling and sending requests!
     * @param session Active client Session
     * @param packet Packet received from the Server
     * @param accessToken PremiumSession accessToken
     * @param uuid UUID of Current Minecraft Session (Received in ServerLoginSuccessPacket)
     * @param proxy Proxy to make request through
     * @return Returns request state. if true the request was sent successfully, if false some error occurred (Enable DEBUG)
     */
    public static final boolean parseEncryption(final Session session, final ServerLoginEncryptionRequestPacket packet,
                                                final String accessToken, final UUID uuid, final Proxy proxy) {
        try {
            final SecretKey secretkey = CryptManager.createNewSharedKey();
            final PublicKey publicKey = packet.getPublicKey();
            final String serverId = (new BigInteger(CryptManager.getServerIdHash(packet.getHashedServerId(), publicKey, secretkey))).toString(16);
            final JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty("accessToken", accessToken);
            jsonObject.addProperty("selectedProfile", uuid.toString().replace("-", ""));
            jsonObject.addProperty("serverId", serverId);

            HttpUtil.postJsonRequest("https://sessionserver.mojang.com/session/minecraft/join", jsonObject.toString(), proxy);
            session.sendPacket(new ClientLoginEncryptionResponsePacket(secretkey, publicKey, packet.getVerifyToken()));
            session.enableEncryption(secretkey);
            return true;
        } catch(Throwable throwable) {
            if(MCProtocol.DEBUG) throwable.printStackTrace();
            return false;
        }
    }

    public static final void parseEncryption(final ClientLoginEncryptionResponsePacket encryptionResponsePacket) {
        //TODO
    }

    public static final PremiumSession makeSession(final String login,final String password){
        return new PremiumSession(login,password);
    }


    /**
     * Premium Session object
     * Made to be easier to use.
     */
    @Getter
    @RequiredArgsConstructor
    public static class PremiumSession {

        /**
         * Username(Email) and password for Minecraft Account
         */
        private final String login, password;

        /**
         * Authentication response data.
         */
        private String username, accessToken, clientToken;

        /**
         * Profile User ID
         */
        private UUID selectedProfileUID;

        /**
         * Logging in to the account.
         * @param proxy Proxy to connect through
         */
        public void authenticate(Proxy proxy) throws AuthenticationUnavailableException, RequestException {
            final AuthenticationResponse authenticationResponse = OpenMCAuthenticator.authenticate(login, password,null, proxy);
            if (Objects.nonNull(authenticationResponse.getSelectedProfile())) {
                username = authenticationResponse.getSelectedProfile().getName();
                selectedProfileUID = authenticationResponse.getSelectedProfile().getUUID();
            }

            accessToken = authenticationResponse.getAccessToken();
            clientToken = authenticationResponse.getClientToken();
        }

        /**
         * Checking if the Account is ready to be used!
         * @return Ready state
         */
        public boolean isReady() {
            return Objects.nonNull(username) && Objects.nonNull(accessToken) && Objects.nonNull(clientToken);
        }


        /**
         * Invalidating account
         * @param proxy Proxy to connect through
         */
        public void invalidate(final Proxy proxy) throws AuthenticationUnavailableException, RequestException {
            OpenMCAuthenticator.invalidate(accessToken, clientToken, proxy);
        }

        /**
         * Logging out of a Account
         * @param proxy Proxy to connect through
         */
        public void signout(final Proxy proxy) throws AuthenticationUnavailableException, RequestException {
            OpenMCAuthenticator.signout(login, password, clientToken, proxy);
        }

    }

}
