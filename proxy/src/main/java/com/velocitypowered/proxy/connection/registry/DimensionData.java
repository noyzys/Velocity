package com.velocitypowered.proxy.connection.registry;

import com.google.common.base.Preconditions;
import com.velocitypowered.api.network.ProtocolVersion;
import net.kyori.nbt.CompoundTag;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class DimensionData {
  private final String registryIdentifier;
  private final @Nullable Integer dimensionId;
  private final boolean isNatural;
  private final float ambientLight;
  private final boolean isShrunk;
  private final boolean isUltrawarm;
  private final boolean hasCeiling;
  private final boolean hasSkylight;
  private final boolean isPiglinSafe;
  private final boolean doBedsWork;
  private final boolean doRespawnAnchorsWork;
  private final boolean hasRaids;
  private final int logicalHeight;
  private final String burningBehaviourIdentifier;
  private final @Nullable Long fixedTime;
  private final @Nullable Boolean createDragonFight;
  private final @Nullable Double coordinateScale;

  /**
   * Initializes a new {@link DimensionData} instance.
   * @param registryIdentifier the identifier for the dimension from the registry.
   * @param dimensionId the dimension ID contained in the registry (the "id" tag)
   * @param isNatural indicates if the dimension use natural world generation (e.g. overworld)
   * @param ambientLight the light level the client sees without external lighting
   * @param isShrunk indicates if the world is shrunk, aka not the full 256 blocks (e.g. nether)
   * @param isUltrawarm internal dimension warmth flag
   * @param hasCeiling indicates if the dimension has a ceiling layer
   * @param hasSkylight indicates if the dimension should display the sun
   * @param isPiglinSafe indicates if piglins should naturally zombify in this dimension
   * @param doBedsWork indicates if players should be able to sleep in beds in this dimension
   * @param doRespawnAnchorsWork indicates if player respawn points can be used in this dimension
   * @param hasRaids indicates if raids can be spawned in the dimension
   * @param logicalHeight the natural max height for the given dimension
   * @param burningBehaviourIdentifier the identifier for how burning blocks work in the dimension
   * @param fixedTime optional. If set to any game daytime value will deactivate time cycle
   * @param createDragonFight optional. Internal flag used in the end dimension
   * @param coordinateScale optional, unknown purpose
   */
  public DimensionData(String registryIdentifier,
      @Nullable Integer dimensionId,
      boolean isNatural,
      float ambientLight, boolean isShrunk, boolean isUltrawarm,
      boolean hasCeiling, boolean hasSkylight,
      boolean isPiglinSafe, boolean doBedsWork,
      boolean doRespawnAnchorsWork, boolean hasRaids,
      int logicalHeight, String burningBehaviourIdentifier,
      @Nullable Long fixedTime, @Nullable Boolean createDragonFight,
      @Nullable Double coordinateScale) {
    Preconditions.checkNotNull(
        registryIdentifier, "registryIdentifier cannot be null");
    Preconditions.checkArgument(registryIdentifier.length() > 0,
        "registryIdentifier cannot be empty");
    Preconditions.checkArgument(logicalHeight >= 0, "localHeight must be >= 0");
    Preconditions.checkNotNull(
        burningBehaviourIdentifier, "burningBehaviourIdentifier cannot be null");
    Preconditions.checkArgument(burningBehaviourIdentifier.length() > 0,
        "burningBehaviourIdentifier cannot be empty");
    this.registryIdentifier = registryIdentifier;
    this.dimensionId = dimensionId;
    this.isNatural = isNatural;
    this.ambientLight = ambientLight;
    this.isShrunk = isShrunk;
    this.isUltrawarm = isUltrawarm;
    this.hasCeiling = hasCeiling;
    this.hasSkylight = hasSkylight;
    this.isPiglinSafe = isPiglinSafe;
    this.doBedsWork = doBedsWork;
    this.doRespawnAnchorsWork = doRespawnAnchorsWork;
    this.hasRaids = hasRaids;
    this.logicalHeight = logicalHeight;
    this.burningBehaviourIdentifier = burningBehaviourIdentifier;
    this.fixedTime = fixedTime;
    this.createDragonFight = createDragonFight;
    this.coordinateScale = coordinateScale;
  }

  public String getRegistryIdentifier() {
    return registryIdentifier;
  }

  public @Nullable Integer getDimensionId() {
    return dimensionId;
  }

  public boolean isNatural() {
    return isNatural;
  }

  public float getAmbientLight() {
    return ambientLight;
  }

  public boolean isShrunk() {
    return isShrunk;
  }

  public boolean isUltrawarm() {
    return isUltrawarm;
  }

  public boolean hasCeiling() {
    return hasCeiling;
  }

  public boolean hasSkylight() {
    return hasSkylight;
  }

  public boolean isPiglinSafe() {
    return isPiglinSafe;
  }

  public boolean doBedsWork() {
    return doBedsWork;
  }

  public boolean doRespawnAnchorsWork() {
    return doRespawnAnchorsWork;
  }

  public boolean hasRaids() {
    return hasRaids;
  }

  public int getLogicalHeight() {
    return logicalHeight;
  }

  public String getBurningBehaviourIdentifier() {
    return burningBehaviourIdentifier;
  }

  public @Nullable Long getFixedTime() {
    return fixedTime;
  }

  public @Nullable Boolean getCreateDragonFight() {
    return createDragonFight;
  }

  public @Nullable Double getCoordinateScale() {
    return coordinateScale;
  }

  /**
   * Parses a given CompoundTag to a DimensionData instance.
   * @param dimTag the compound from the registry to read
   * @param version the protocol version from the registry
   * @return game dimension data
   */
  public static DimensionData decodeCompoundTag(CompoundTag dimTag, ProtocolVersion version) {
    Preconditions.checkNotNull(dimTag, "CompoundTag cannot be null");
    String registryIdentifier = dimTag.getString("name");
    CompoundTag details;
    Integer dimensionId = null;
    if (version.compareTo(ProtocolVersion.MINECRAFT_1_16_2) >= 0) {
      dimensionId = dimTag.getInt("id");
      details = dimTag.getCompound("element");
    } else {
      details = dimTag;
    }
    boolean isNatural = details.getBoolean("natural");
    float ambientLight = details.getFloat("ambient_light");
    boolean isShrunk = details.getBoolean("shrunk");
    boolean isUltrawarm = details.getBoolean("ultrawarm");
    boolean hasCeiling = details.getBoolean("has_ceiling");
    boolean hasSkylight = details.getBoolean("has_skylight");
    boolean isPiglinSafe = details.getBoolean("piglin_safe");
    boolean doBedsWork = details.getBoolean("bed_works");
    boolean doRespawnAnchorsWork = details.getBoolean("respawn_anchor_works");
    boolean hasRaids = details.getBoolean("has_raids");
    int logicalHeight = details.getInt("logical_height");
    String burningBehaviourIdentifier = details.getString("infiniburn");
    Long fixedTime = details.contains("fixed_time")
        ? details.getLong("fixed_time") : null;
    Boolean hasEnderdragonFight = details.contains("has_enderdragon_fight")
        ? details.getBoolean("has_enderdragon_fight") : null;
    Double coordinateScale = details.contains("coordinate_scale")
        ? details.getDouble("coordinate_scale") : null;
    return new DimensionData(
        registryIdentifier, dimensionId, isNatural, ambientLight, isShrunk,
        isUltrawarm, hasCeiling, hasSkylight, isPiglinSafe, doBedsWork, doRespawnAnchorsWork,
        hasRaids, logicalHeight, burningBehaviourIdentifier, fixedTime, hasEnderdragonFight,
        coordinateScale);
  }

  /**
   * Encodes the Dimension data as CompoundTag.
   * @param version the version to serialize as
   * @return compound containing the dimension data
   */
  public CompoundTag encodeAsCompoundTag(ProtocolVersion version) {
    CompoundTag details = serializeDimensionDetails();
    if (version.compareTo(ProtocolVersion.MINECRAFT_1_16_2) >= 0) {
      CompoundTag parent = new CompoundTag();
      parent.putString("name", registryIdentifier);
      if (dimensionId == null) {
        throw new IllegalStateException("Tried to serialize a 1.16.2+ dimension registry entry "
            + "without an ID");
      }
      parent.putInt("id", dimensionId);
      parent.put("element", details);
      return parent;
    } else {
      details.putString("name", registryIdentifier);
      return details;
    }
  }

  private CompoundTag serializeDimensionDetails() {
    CompoundTag ret = new CompoundTag();
    ret.putBoolean("natural", isNatural);
    ret.putFloat("ambient_light", ambientLight);
    ret.putBoolean("shrunk", isShrunk);
    ret.putBoolean("ultrawarm", isUltrawarm);
    ret.putBoolean("has_ceiling", hasCeiling);
    ret.putBoolean("has_skylight", hasSkylight);
    ret.putBoolean("piglin_safe", isPiglinSafe);
    ret.putBoolean("bed_works", doBedsWork);
    ret.putBoolean("respawn_anchor_works", doRespawnAnchorsWork);
    ret.putBoolean("has_raids", hasRaids);
    ret.putInt("logical_height", logicalHeight);
    ret.putString("infiniburn", burningBehaviourIdentifier);
    if (fixedTime != null) {
      ret.putLong("fixed_time", fixedTime);
    }
    if (createDragonFight != null) {
      ret.putBoolean("has_enderdragon_fight", createDragonFight);
    }
    if (coordinateScale != null) {
      ret.putDouble("coordinate_scale", coordinateScale);
    }
    return ret;
  }
}
