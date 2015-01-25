package StrgWar.map.providers;

import javafx.geometry.Point2D;
import StrgWar.map.changeable.ChangeableMap;
import StrgWar.map.changeable.ChangeableNode;
import StrgWar.map.changeable.IChangeableMapProvider;
import StrgWar.map.loader.IMapLoader;
import StrgWar.map.loader.RawNode;
import StrgWar.map.readonly.IReadonlyMapProvider;
import StrgWar.map.readonly.ReadonlyMap;

public class MapFromFileProvider implements IChangeableMapProvider, IReadonlyMapProvider
{
	public MapFromFileProvider(IMapLoader loader)
	{
		_changeAbleMap = null;
		_readonlyMap = null;
		
		_loader = loader;
	}

	@Override
	public ReadonlyMap GetReadOnlyMap()
	{
		if (_readonlyMap == null) 
			InitMaps();

		return _readonlyMap;
	}

	@Override
	public ChangeableMap GetChangeableMap()
	{
		if (_changeAbleMap == null) 
			InitMaps();

		return _changeAbleMap;
	}

	private void InitMaps()
	{
		_changeAbleMap = new ChangeableMap();
		
		
		for (RawNode rn : _loader.GetNodes())
		{
			ChangeableNode nd = new ChangeableNode(rn.name, rn.occupant, rn.startSize, rn.unitsPerSecond);
			
			nd.SetPosition(new Point2D(rn.x, rn.y));
			
			_changeAbleMap.nodes.add(nd);
		}
		
		
		_readonlyMap = new ReadonlyMap();
		
		for (ChangeableNode nd : _changeAbleMap.nodes)
		{
			_readonlyMap.nodes.add(nd);
		}
	}

	private ChangeableMap _changeAbleMap;
	private ReadonlyMap _readonlyMap;
	private IMapLoader _loader;
}