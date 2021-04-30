package it.polito.group19.lab2.security

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAuthenticationTokenFilter(val jwtUtils: JwtUtils): OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        var token: String;
        var header = request.getHeader("Authorization")
        if (header.startsWith("Bearer ")) {
            token = header.substring(7)
            if (jwtUtils.validateJwtToken(token)) {
                val userDetailsDTO = jwtUtils.getDetailsFromJwtToken(token)
                var usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(userDetailsDTO, null, userDetailsDTO.authorities)
                usernamePasswordAuthenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)

                SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
            }
        }

    }

}