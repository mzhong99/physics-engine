
public class PointEntity extends Entity {
	
	public PointEntity() {
		this(0, 0);
	}

	public PointEntity(double p_x, double p_y) {
		this(p_x, p_y, 0, 0);
	}

	public PointEntity(double p_x, double p_y, double v_x, double v_y) {
		super();

		entityTypes.add(EntityType.MOVABLE);
		entityTypes.add(EntityType.COLLIDABLE);
		entityTypes.add(EntityType.VIEWABLE);
		entityTypes.add(EntityType.WEIGHABLE);

		components.put(ComponentType.POSITION, new Vector2D(p_x, p_y));
		components.put(ComponentType.VELOCITY, new Vector2D(v_x, v_y));
		components.put(ComponentType.VIEW, new Rectangle(20, 20));
	}
}