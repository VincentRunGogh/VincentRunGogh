from scipy.spatial import KDTree
import networkx as nx

def matching_user_path_to_roads(user_drawn_path, pedestrian_df):

    # kdTree 이용해서 보행자 도로와 매핑시키기
    # pedestrian_df에서 데이터 가져오기
    pedestrian_data = pedestrian_df.select("way_id", "node_id", "lat", "lng").collect()
    # KD트리, 배열
    node_coords = []
    nodes = {}
    ways = {}

    # 노드와 웨이 데이터를 수집
    G = nx.Graph()  # 도로 네트워크 그래프 생성
    # 쿼리에서 가져온 도로 데이터들
    for row in pedestrian_data:
        node_id = row['node_id']
        lat = row['lat']
        lng = row['lng']
        # dict 형태로 노드 정보 저장하기
        nodes[node_id] = (lat,lng)
        # kd-tree에 사용할 노드 좌표 저장
        node_coords.append((lat,lng))
        G.add_node(node_id, pos=(lat, lng))

    # kd-tree 이용해서 얻은 좌표를 통해 노드간 연결 그래프로 추가
    for row in pedestrian_data:
        way_id = row['way_id']
        node_id = row['node_id']

        # way_id별 node_id 리스트 형태로 저장해두기
        if way_id not in nodes:
            ways[way_id] = []

        ways[way_id].append(node_id)

    # 각 way_id에 대해 노드 리스트 연결
    for way_id, node_list in ways.items():
        for i in range(len(node_list) - 1):
            # 노드 간에 엣지를 추가하여 도로 네트워크를 구축
            G.add_edge(node_list[i], node_list[i + 1])

    # kdtree 생성
    kdtree = KDTree(node_coords)

    # 사용자가 그린 경로 좌표 (latList, lngList)
    # leaflet에서 좌표 받아온 것 -> redis에 저장된 상태
    # 받아오기
    # 2. 사용자가 그린 그림의 좌표 획득 (request 요청한 매개변수)

    # 3. 각 그림 좌표와 가장 가까운 도로 노드 찾기 및 경로 연결성 확인
    matched_nodes = []
    matched_nodes_set = set()  # 중복 체크를 위한 집합

    for coord in user_drawn_path:

        distance, index = kdtree.query(coord)
        nearest_node = node_coords[index]

        node_id = list(nodes.keys())[list(nodes.values()).index(nearest_node)]

        if node_id not in matched_nodes_set:
            matched_nodes.append(node_id)
            matched_nodes_set.add(node_id)

    # matched_nodes에 저장된 각 노드 ID에 대한 좌표를 추출
    # response할 값
    route_coords = [nodes[node_id] for node_id in matched_nodes]
    return route_coords