BASE_URl=http://localhost:9090

curl $BASE_URl/u/add \
-X POST \
-H "Content-Type: application/json" \
-d '{"cardId":"20009100359","name":"江志航","major":"计算机科学与技术","college":"西安电子科技大学","department":"计算机科学与技术院","classId":"2003018"}'

curl $BASE_URl/u/add \
-X POST \
-H "Content-Type: application/json" \
-d '{"cardId":"20009200657","name":"马丁","major":"软件工程","college":"西安电子科技大学","department":"计算机科学与技术院","classId":"2003068"}'

curl $BASE_URl/u/add \
-X POST \
-H "Content-Type: application/json" \
-d '{"cardId":"123456","name":"test","major":"test-major","college":"test-college","department":"test-department","classId":"123456"}'

