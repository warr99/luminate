--KEYS[1]: 限流 key
--ARGV[1]: 限流窗口,毫秒
--ARGV[2]: 当前时间戳（作为score）
--ARGV[3]: 阈值
--ARGV[4]: score 对应的唯一value
-- 从有序集合 KEYS[1] 中删除分值在0到 ARGV[2]-ARGV[1] 之间的所有元素,清理过期元素
redis.call('zremrangeByScore', KEYS[1], 0, ARGV[2]-ARGV[1])
-- 获取有序集合 KEYS[1] 中当前的元素数量，并将结果赋值给本地变量 res
local res = redis.call('zcard', KEYS[1])
-- 是否超过阈值
if (res == nil) or (res < tonumber(ARGV[3])) then
    -- 元素数量小于阈值，则向有序集合中添加一个新元素，score = ARGV[2],value = ARGV[4]
    redis.call('zadd', KEYS[1], ARGV[2], ARGV[4])
    -- 设置该有序集合的过期时间为 ARGV[1]/1000 秒
    redis.call('expire', KEYS[1], ARGV[1]/1000)
    -- 返回0 -> 元素数量小于阈值(信息允许被发送)
    return 0
else
    -- 返回1 -> 元素数量大于等于阈值(信息允许被发送)
    return 1
end
