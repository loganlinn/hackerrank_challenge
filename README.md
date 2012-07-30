## HackerRank Challenge Solver

This is a small program I put together to solve the "SpaceX" challenges
on (hackerrank.com)[http://www.hackerrank.com].

You can access these challenges manually by signing up/in and using the
`challenge <N>` command. Currently, challenges 1 - 10,000 can be solved
with this program.

This program requests a challenge, solves it, then submits the answer.

It uses environment variables, `HACKERRANK_USERNAME` and
`HACKERRANK_PASSWORD` to login and obtain a session.

## Usage

With (Leinigen)[https://github.com/technomancy/leiningen/] installed,
you can solve challenges in batches by running the command:

```bash
 HACKERRANK_USERNAME=<username> HACKERRANK_PASSWORD=<password> lein run -m hackerrank.httpsolver 1 10
```

This will solve 10 challenges, starting at test 1.

After the command finishes, you could change the last two arguments to
11 and 1000 to solve thte next 1000 challenges.

## Disclaimer

I am still learning Clojure and this code probably isn't the best
example. You have been warned.

## License

Distributed under the Eclipse Public License, the same as Clojure.
