using Carer.Common;
using Carer.Model.DomainModels;
using System;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace Carer.Service
{
    public interface IAppliedUserService
    {
        Task DeleteAppliedUserAsync(Guid userId, Guid adId);
        Task<List<AppliedUserDomainModel>> GetAllAppliedUserAsync(AppliedUserFilter filter);
        Task PostAppliedUserAsync(AppliedUserDomainModel appliedUser);
        Task PutAppliedUserAsync(Guid userId, Guid adId, string status);
    }
}