
import java.util.LinkedList;

public class DijkstraFunctions {

	private int nodeNmber;
	private int shortDistance;
	private String shortestRoute = "";
	private LinkedList<GraphFunctions> list = new LinkedList<GraphFunctions>();

	public int getDistance() {
		return shortDistance;
	}
	public void setDistance(int distance)
	{
		this.shortDistance = distance;
	}

	public LinkedList<GraphFunctions> getList() {
		return list;
	}
	public void setList(LinkedList<GraphFunctions> list) {
		this.list = list;
	}
	public int getNNmber() {
		return nodeNmber;
	}
	public void setNNmber(int NNmber) {
		this.nodeNmber = NNmber;
	}
	public String getShortDIST() {
		return shortestRoute;
	}
	public void setShortDIST(String shortestRoute) {
		this.shortestRoute = shortestRoute;
	}


}
