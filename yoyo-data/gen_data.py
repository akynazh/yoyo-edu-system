# python3 yoyo-data/gen_data.py 20009100359
# python3 yoyo-data/gen_data.py 20009200657
import time
import random
import asyncio
import aiohttp
import sys

BASE_URL = "http://localhost:9090/e/add"
INTERVAL = 5
CARD_ID = "123456"
if len(sys.argv) > 1:
    CARD_ID = sys.argv[1]
print(CARD_ID)


async def gen_data(d_type: int, data: dict):
    url = f"{BASE_URL}/{d_type}"
    async with aiohttp.ClientSession() as session:
        async with session.post(url=url, json=data):
            pass


async def gen_data_1():
    await gen_data(
        1,
        {
            "cardId": CARD_ID,
            "heartRate": random.randint(60, 100),
            "bloodOxygen": random.randint(93, 98),
            "microCirculation": random.randint(10, 40),
            "time": int(time.time() * 1000),
        },
    )


async def gen_data_2():
    await gen_data(
        2,
        {
            "cardId": CARD_ID,
            "above": random.randint(500, 2500),
            "below": random.randint(500, 2500),
            "time": int(time.time() * 1000),
        },
    )


async def gen_data_3():
    await gen_data(
        3,
        {
            "cardId": CARD_ID,
            "shrink": random.randint(0, 200),
            "diastolic": random.randint(0, 200),
            "time": int(time.time() * 1000),
        },
    )


async def gen_data_4():
    await gen_data(
        4,
        {
            "cardId": CARD_ID,
            "leftHead": random.randint(-1000, 1000),
            "rightHead": random.randint(-1000, 1000),
            "leftEar": random.randint(-1000, 1000),
            "rightEar": random.randint(-1000, 1000),
            "time": int(time.time() * 1000),
        },
    )


async def gen_data_5():
    cur_time = int(time.time() * 1000)
    t = random.randint(1, 3)
    await gen_data(
        5,
        {
            "cardId": CARD_ID,
            "imagePath": f"images/test/{CARD_ID}_p{t}.png",
            "remark": f"test-{t}",
            "time": cur_time,
        },
    )


async def gen_data_6():
    await gen_data(
        6,
        {
            "cardId": CARD_ID,
            "result": random.randint(1, 4),
            "time": int(time.time() * 1000),
        },
    )


async def main():
    i = 1
    while True:
        print(f"start-{i}-interval-{INTERVAL}")
        tasks = [
            asyncio.ensure_future(gen_data_1()),
            asyncio.ensure_future(gen_data_2()),
            asyncio.ensure_future(gen_data_3()),
            asyncio.ensure_future(gen_data_4()),
            asyncio.ensure_future(gen_data_5()),
            asyncio.ensure_future(gen_data_6()),
        ]
        await asyncio.wait(tasks)
        await asyncio.sleep(INTERVAL)
        i += 1


if __name__ == "__main__":
    asyncio.run(main())
