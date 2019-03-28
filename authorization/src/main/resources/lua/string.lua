--
-- Created by IntelliJ IDEA.
-- User: Administrator
-- Date: 2019/3/28 0028
-- Time: 17:38
-- To change this template use File | Settings | File Templates.
--
local times = redis.call('incr',KEYS[1])
if times == 1 then
    redis.call('expire',KEYS[1],ARGV[1])
end if times > 5 then
    return 0
end return 1

