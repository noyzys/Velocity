package com.velocitypowered.proxy.network.packet.clientbound;

import static com.velocitypowered.proxy.network.ProtocolUtils.writeString;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.velocitypowered.api.network.ProtocolVersion;
import com.velocitypowered.proxy.network.packet.Packet;
import com.velocitypowered.proxy.network.packet.PacketDirection;
import com.velocitypowered.proxy.network.packet.PacketHandler;
import io.netty.buffer.ByteBuf;

public class ClientboundHeaderAndFooterPacket implements Packet {
  public static final Decoder<ClientboundHeaderAndFooterPacket> DECODER = Decoder.method(ClientboundHeaderAndFooterPacket::new);

  private static final String EMPTY_COMPONENT = "{\"translate\":\"\"}";
  private static final ClientboundHeaderAndFooterPacket RESET
      = new ClientboundHeaderAndFooterPacket();

  private final String header;
  private final String footer;

  public ClientboundHeaderAndFooterPacket() {
    this(EMPTY_COMPONENT, EMPTY_COMPONENT);
  }

  public ClientboundHeaderAndFooterPacket(String header, String footer) {
    this.header = Preconditions.checkNotNull(header, "header");
    this.footer = Preconditions.checkNotNull(footer, "footer");
  }

  @Override
  public void encode(ByteBuf buf, PacketDirection direction, ProtocolVersion version) {
    writeString(buf, header);
    writeString(buf, footer);
  }

  @Override
  public boolean handle(PacketHandler handler) {
    return handler.handle(this);
  }

  public String getHeader() {
    return header;
  }

  public String getFooter() {
    return footer;
  }

  public static ClientboundHeaderAndFooterPacket reset() {
    return RESET;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
      .add("header", this.header)
      .add("footer", this.footer)
      .toString();
  }
}
