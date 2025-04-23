-- 比较线程标识与锁的标识是否一致
if(redis.call('get',KEYS[1]) == ARGV[1]) then
    --释放锁del key
    return redis.call('del',KEYS[1])
end
return 0